package com.jtd.recharge.sender;

import com.jtd.recharge.bean.ChargeRequest;
import java.util.List;
import java.util.Random;

/**
 * @autor jipengkun
 */
public class LoadBalance {

    private static Random random = new Random();

    public static ChargeRequest loadRoute(List<ChargeRequest> supplyList) throws Exception {
        Integer weightSum = 0;
        for (ChargeRequest chargeRequest : supplyList) {
            weightSum += chargeRequest.getWeight();
        }

        if (weightSum <= 0) {
            System.err.println("Error: weightSum=" + weightSum.toString());
            throw new Exception("List长度为空");
        }
        Integer n = random.nextInt(weightSum); // n in [0, weightSum)
        Integer m = 0;

        ChargeRequest supply = new ChargeRequest();

        for (ChargeRequest chargeRequest : supplyList) {
            if (m <= n && n < m + chargeRequest.getWeight()) {
                System.out.println("This Random Supply is "+chargeRequest.getSupplyName());

                supply = chargeRequest;
                break;
            }
            m += chargeRequest.getWeight();
        }
        return supply;
    }

}
