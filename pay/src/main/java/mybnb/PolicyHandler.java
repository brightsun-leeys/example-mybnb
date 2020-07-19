package mybnb;

import mybnb.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    private PaymentRepository paymentRepository;

    /*
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){
    }
     */

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverBookCanceled_Cancel(@Payload BookCanceled bookCanceled){
        if(bookCanceled.isMe()){
            Payment payment = new Payment();
            payment.setBookId(bookCanceled.getId());
            payment.setGuestId(bookCanceled.getGuestId());
            payment.setRoomId(bookCanceled.getRoomId());
            payment.setPrice(bookCanceled.getPrice());
            payment.setStatus("PayCanceled");
            paymentRepository.save(payment);
        }
    }

}
