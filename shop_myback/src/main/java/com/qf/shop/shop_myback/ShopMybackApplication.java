package com.qf.shop.shop_myback;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(FdfsClientConfig.class)
public class ShopMybackApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShopMybackApplication.class, args);
	}
}
