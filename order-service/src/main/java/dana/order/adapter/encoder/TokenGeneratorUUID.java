package dana.order.adapter.encoder;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenGeneratorUUID {
    public String generateToken() {
        return UUID.randomUUID().toString();
    }
}
