package exception;

public class SkuNotExistException extends Exception {



  /**
   * Throw when Sku not exist for some product information.
   * 
   * Exception SkuNotExistException
   */

  public SkuNotExistException(String message) {
    super(message);
  }

  private static final long serialVersionUID = -6334741273099221662L;

}
