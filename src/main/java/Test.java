import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.BitSet;

@Configuration
public class Test {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        SimpleModule bitSetModule = new SimpleModule("BitSetModule");
        bitSetModule.addSerializer(new BitSetSerializer());
        bitSetModule.addDeserializer(BitSet.class, new BitSetDeserializer());
        return builder -> builder.modules(bitSetModule);
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        SimpleModule bitSetModule = new SimpleModule("BitSetModule");
        bitSetModule.addSerializer(new BitSetSerializer());
        bitSetModule.addDeserializer(BitSet.class, new BitSetDeserializer());
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .registerModule(bitSetModule);
    }
}
