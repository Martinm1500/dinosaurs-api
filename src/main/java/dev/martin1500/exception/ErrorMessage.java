package dev.martin1500.exception;

public class ErrorMessage {

    public static String speciesIsRequired = "The 'Species' cannot be Blank, Empty or Null.";
    public static String orderIsRequired = "The 'Order' is required.";
    public static String subOrderIsRequired = "The 'SubOrder' is required.";
    public static String geologicalPeriodIsRequired = "The 'GeologicalPeriod' is required.";
    public static String ResourceNotFound = "Resource not found";
    public static String invalidOrder = "The 'Order' must be SAURISCHIA, or ORNITHISCHIA.";
    public static String invalidSubOrder = "The 'SubOrder' must be THEROPODA, SAUROPODOMORPHA, ORNITHOPODA, MARGINOCEPHALIA, or THYREOPHORA.";
    public static String invalidGeologicalPeriod = "The 'GeologicalPeriod' must be TRIASSIC, JURASSIC, or CRETACEOUS.";
}
