package ru.job4j.weather;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WeatherService {

    private final Map<Integer, Weather> weathers = new ConcurrentHashMap<>();
    {
        weathers.put(1, new Weather(1, "Msc", 20));
        weathers.put(2, new Weather(2, "SPb", 15));
        weathers.put(3, new Weather(3, "Bryansk", 15));
        weathers.put(4, new Weather(4, "Smolensk", 25));
        weathers.put(5, new Weather(5, "Kiev", 15));
        weathers.put(6, new Weather(6, "Minsk", 15));
    }

    public Mono<Weather> findById(Integer id) {
        return Mono.justOrEmpty(weathers.get(id));
    }

    public Flux<Weather> all() {
        return Flux.fromIterable(weathers.values());
    }

    public Mono<Weather> findHottestCity() {
        Comparator<Weather> comparator = Comparator.comparing(Weather::getTemperature);
        List<Weather> list = new ArrayList<>(weathers.values());
        Weather maxObject = list.stream().max(comparator).get();
        return Mono.justOrEmpty(maxObject);
    }

    public Flux<Weather> cityGreatThen(Integer temperature) {
        List<Weather> rst = new ArrayList<>();
        List<Weather> list = new ArrayList<>(weathers.values());
        for (Weather current : list) {
            if (current.getTemperature() > temperature) {
                rst.add(current);
            }
        }
        return Flux.fromIterable(rst);
    }
}
