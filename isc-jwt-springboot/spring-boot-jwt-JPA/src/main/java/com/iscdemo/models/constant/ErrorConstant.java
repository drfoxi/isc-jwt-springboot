package com.iscdemo.models.constant;

public class ErrorConstant {

    public static final int USER_ALREDY_EXIST = 30100;
    public static final String USER_ALREDY_EXIST_MESSAGE = "کاربر دیگری با این شناسه(Username) وجود دارد.";


    public static final int QUANTITY_MUST_BE_GREATER_THAN_ZERO = 30101;
    public static final String QUANTITY_MUST_BE_GREATER_THAN_ZERO_MESSAGE = "تعداد هر سفارش باید بیشتر از صفر باشد.";

    public static final int PRODUCT_NOT_FOUND = 30102;
    public static final String PRODUCT_NOT_FOUND_MESSAGE = "محصول در سیستم ثبت نشده است";

    public static final int MAX_MOBILE_ALLOWED_PAYMENT = 30103;
    public static final String MAX_MOBILE_ALLOWED_PAYMENT_MESSAGE = "پرداخت این مبلغ از درگاه موبایل امکان پذیر نمیباشد.";

    public static final int PRODUCT_NOT_AVAILABLE = 30104;
    public static final String PRODUCT_NOT_AVAILABLE_MESSAGE = "این محصول قابل فروش نیست.";


}
