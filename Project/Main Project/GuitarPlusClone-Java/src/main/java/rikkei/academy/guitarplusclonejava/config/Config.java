package rikkei.academy.guitarplusclonejava.config;

import java.text.NumberFormat;
import java.util.Locale;

public class Config {
   public static Locale locale = new Locale("vi", "VN");
   public static NumberFormat currencyVN = NumberFormat.getCurrencyInstance(locale);
}
