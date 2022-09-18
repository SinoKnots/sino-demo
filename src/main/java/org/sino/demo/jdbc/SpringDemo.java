package org.sino.demo.jdbc;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

public class SpringDemo {

}

@EnableTransactionManagement
class TradeOrderDAO {

    @Transactional(isolation = Isolation.DEFAULT, rollbackForClassName = "SpringDemo")
    public void updateTradeOrder(TradeOrderData order) throws Exception {
        try {
            TradeOrderDAO dao = new TradeOrderDAO();
            dao.updateTradeOrder(order);
        } catch (Exception e) {
            throw e;
        }
    }

    public static void main(String[] args) {

    }


}


