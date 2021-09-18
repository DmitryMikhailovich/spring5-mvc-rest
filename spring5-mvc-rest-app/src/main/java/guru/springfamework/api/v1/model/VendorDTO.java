package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VendorDTO {
    private Long id;

    @ApiModelProperty(name = "Vendor's name", required = true, example = "ACME Corporation")
    @JsonProperty("name")
    private String name;

    @ApiModelProperty(name = "Vendor URL", readOnly = true)
    @JsonProperty("vendor_url")
    private String vendorUrl;

}
