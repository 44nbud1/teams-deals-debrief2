package dana.order.usecase.port;

public interface VoucherRepository {
    Boolean validateExpiration(Integer idVoucher);
    Boolean validateQuantity(Integer idVoucher);
    Integer insertNewOrder(String idUser, Integer idVoucher);
    Boolean isVoucherExists(Integer idVoucher);
}
