package com.food.apnajalpaan.service;

import com.food.apnajalpaan.model.Payment;
import com.food.apnajalpaan.repository.PaymentRepository;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PaymentService {
    @Autowired
    PaymentRepository repository;

    public Mono<Payment> createOrder(Double amount,String userId) throws RazorpayException {
        Dotenv dotenv = Dotenv.load();
        Payment payment = new Payment();
        try {
            RazorpayClient razorpay = new RazorpayClient(dotenv.get("RZP_KEY_ID"),dotenv.get("RZP_SECRET_KEY"));
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount*100); // amount in the smallest currency unit
            orderRequest.put("currency", dotenv.get("RZP_CURRENCY"));
            orderRequest.put("receipt", "order_rcptid_11");

            com.razorpay.Order order = razorpay.Orders.create(orderRequest);
            // save order in database....
            payment.setOrderId(order.get("id"));
            payment.setStatus(order.get("status"));
            payment.setReceipt(order.get("receipt"));
            payment.setUserId(userId);
            System.out.println(order);
        } catch (RazorpayException e) {
            // Handle Exception
            System.out.println(e.getMessage());
        }
        return repository.insert(payment);
    }

    // check for update order
    public Mono<Payment> updateOrder(Mono<Payment> paymentMono){
        return paymentMono.flatMap(
            x -> {
                return repository.findByOrderId(x.getOrderId()).flatMap(
                    res -> {
                        if(x.getPaymentId()!=null) res.setPaymentId(x.getPaymentId());
                        if(x.getStatus()!=null) res.setStatus(x.getStatus());
                        return Mono.just(res);
                    }
                ).flatMap(repository::save);
            }
        );
    }
}
