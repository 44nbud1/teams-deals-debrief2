package dana.order.usecase.broadcast.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

public class Voucher implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long idVoucher;
    private String idUser;
    private String voucherName;
    private Double voucherPrice;
    private Double discount;
    private Double maxDiscount;
    private Integer quota;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date expiredDate;
    private Boolean status;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createAt;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updateAt;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(Long idVoucher) {
        this.idVoucher = idVoucher;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName;
    }

    public Double getVoucherPrice() {
        return voucherPrice;
    }

    public void setVoucherPrice(Double voucherPrice) {
        this.voucherPrice = voucherPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getMaxDiscount() {
        return maxDiscount;
    }

    public void setMaxDiscount(Double maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }
}



