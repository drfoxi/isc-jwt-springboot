package com.iscdemo.service;

import com.iscdemo.models.basemodel.InvocationContext;
import com.iscdemo.models.basemodel.MainSecurityContext;
import com.iscdemo.models.constant.ErrorConstant;
import com.iscdemo.models.constant.ProjectConstant;
import com.iscdemo.models.entity.Order;
import com.iscdemo.models.entity.Product;
import com.iscdemo.models.entity.ShoppingCart;
import com.iscdemo.repository.ProductDao;
import com.iscdemo.repository.ShoppingCartDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ShoppingCartService {

    @Autowired
    ShoppingCartDao shoppingCartDao;

    @Autowired
    ProductService productService;

    @Transactional(rollbackOn={Exception.class})
    public InvocationContext<Long> registerOrder(MainSecurityContext msc, ShoppingCart shoppingCart) throws Exception {
        try {
            InvocationContext<Long> ic = new InvocationContext<>();
            long totalAmount = 0;
            InvocationContext<Long> calculateTotalAmountOfShoppingCartIc = calculateTotalAmountOfShoppingCart(msc, shoppingCart);
            if (!calculateTotalAmountOfShoppingCartIc.isSuccessful()) {
                return new InvocationContext<>(calculateTotalAmountOfShoppingCartIc.getErrorCode(),
                        calculateTotalAmountOfShoppingCartIc.getErrorMessage());
            }
            totalAmount = calculateTotalAmountOfShoppingCartIc.getData();
            shoppingCart.setTotalAmount(totalAmount);

            InvocationContext<Boolean> validationIc = doChannelValidation(msc, totalAmount);
            if (!validationIc.isSuccessful()) {
                return new InvocationContext<>(validationIc.getErrorCode(),
                        validationIc.getErrorMessage());
            }
            InvocationContext<Integer> calculateTotalQuantityIc = calculateTotalQuantity(msc, shoppingCart);
            if (!calculateTotalQuantityIc.isSuccessful()) {
                return new InvocationContext<>(calculateTotalQuantityIc.getErrorCode(),
                        calculateTotalQuantityIc.getErrorMessage());
            }
            shoppingCart.setTotalQuantity(calculateTotalQuantityIc.getData());
            shoppingCart.setTrackingCode(Long.valueOf(new Date(System.currentTimeMillis()).getTime()));
            shoppingCartDao.save(shoppingCart);
            ic.setData(shoppingCart.getTrackingCode());
            return ic;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    public InvocationContext<ShoppingCart> fetchShoppingCartByTrackingCode(MainSecurityContext msc, Long trackingCode) throws Exception {
        try {
            InvocationContext<ShoppingCart> ic = new InvocationContext<>();
            ic.setData(shoppingCartDao.findByTrackingCode(trackingCode));
            return ic;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    private InvocationContext<Long> calculateTotalAmountOfShoppingCart(MainSecurityContext msc, ShoppingCart shoppingCart) throws Exception {
        try {
            InvocationContext<Long> ic = new InvocationContext<>();
            long totalAmountOfShoppingCard = 0;
            Product product = null;
            for (Order order : shoppingCart.getOrders()) {
                if (order.getQuantity() <= 0) {
                    return new InvocationContext<>(ErrorConstant.QUANTITY_MUST_BE_GREATER_THAN_ZERO,
                            ErrorConstant.QUANTITY_MUST_BE_GREATER_THAN_ZERO_MESSAGE);
                }

                InvocationContext<Product> productIc = productService.fetchProductById(msc, order.getProduct().getId());
                if (!productIc.isSuccessful()) {
                    return new InvocationContext<>(productIc.getErrorCode(), productIc.getErrorMessage());
                }
                if (!productIc.getData().isAvailable()) {
                    return new InvocationContext<>(ErrorConstant.PRODUCT_NOT_AVAILABLE,
                            ErrorConstant.PRODUCT_NOT_AVAILABLE_MESSAGE);
                }
                totalAmountOfShoppingCard = totalAmountOfShoppingCard + (productIc.getData().getProductPrice() * order.getQuantity());
            }
            ic.setData(totalAmountOfShoppingCard);
            return ic;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    private InvocationContext<Integer> calculateTotalQuantity(MainSecurityContext msc, ShoppingCart shoppingCart) throws Exception {
        try {
            InvocationContext<Integer> ic = new InvocationContext<>();
            Integer totalQuantityOfShoppingCard = 0;
            for (Order order : shoppingCart.getOrders()) {
                if (order.getQuantity() <= 0) {
                    ic.setErrorCode(ErrorConstant.QUANTITY_MUST_BE_GREATER_THAN_ZERO);
                    ic.setErrorMessage(ErrorConstant.QUANTITY_MUST_BE_GREATER_THAN_ZERO_MESSAGE);
                    return ic;
                }
                totalQuantityOfShoppingCard = totalQuantityOfShoppingCard + order.getQuantity();
            }
            ic.setData(totalQuantityOfShoppingCard);
            return ic;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }


    private InvocationContext<Boolean> doChannelValidation(MainSecurityContext msc, Long totalAmount) throws Exception {
        try {
            InvocationContext<Boolean> ic = new InvocationContext<>();
            if (msc.getChannel() == ProjectConstant.CHANNEL_GATEWAY_MOBILE_CODE && totalAmount >=
                    ProjectConstant.MAX_MOBILE_AMOUNT_PAYMENT) {
                return new InvocationContext<>(false, ErrorConstant.MAX_MOBILE_ALLOWED_PAYMENT,
                        ErrorConstant.MAX_MOBILE_ALLOWED_PAYMENT_MESSAGE);
            } else {
                ic.setData(true);
            }
            return ic;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }
}
