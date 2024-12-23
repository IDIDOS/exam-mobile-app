package models;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonClass;
import lombok.Data;

import java.util.Map;

@Data
public class CityDTO {

    @Json(name = "name")
    private String name;

    @Json(name = "local_names")
    private Map<String, String> localNames;

    @Json(name = "lat")
    private double lat;

    @Json(name = "lon")
    private double lon;

    @Json(name = "country")
    private String country;

    @Json(name = "state")
    private String state;
}
