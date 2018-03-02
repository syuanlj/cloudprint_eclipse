package com.nfet.thread;

import com.nfet.PrintProtocol;
import com.nfet.entity.Product;
import com.nfet.service.ProductService;
import com.nfet.util.PushManager;
import com.nfet.util.SpringUtils;

public class ResetProduct implements Runnable {

	private Long productId = null;

	public ResetProduct(Long productId) {
		super();
		this.productId = productId;
	}

	@Override
	public void run() {
		ProductService productService = SpringUtils.getBean("productServiceImpl", ProductService.class);
		Product product = productService.find(this.productId);

		PushManager.getInstance().pushPrintMessage(product.getSn(), PrintProtocol.COMMAND_ID_RESET_PRINTER, null, null);
	}
}