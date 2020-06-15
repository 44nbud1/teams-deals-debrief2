package com.danapprentech.debrief2.voucherservice.service;

import com.danapprentech.debrief2.voucherservice.model.MerchantCategory;

public interface MerchantCategoryService
{
    MerchantCategory findByMerchantCategoryContaining(String merchant);
}
