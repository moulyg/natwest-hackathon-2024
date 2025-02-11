package com.natwest.boa.hackathon.model.payments;

import com.natwest.boa.hackathon.model.common.Links;
import com.natwest.boa.hackathon.model.common.Meta;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * OBWriteDomesticResponse2
 */
@Validated
public class OBWriteDomesticResponse {
    @JsonProperty("Data")
    private OBWriteDataDomesticResponse data = null;

    @JsonProperty("Links")
    private Links links = null;

    @JsonProperty("Meta")
    private Meta meta = null;

    public OBWriteDomesticResponse data(OBWriteDataDomesticResponse data) {
        this.data = data;
        return this;
    }

    /**
     * Get data
     *
     * @return data
     **/
    @NotNull

    @Valid

    public OBWriteDataDomesticResponse getData() {
        return data;
    }

    public void setData(OBWriteDataDomesticResponse data) {
        this.data = data;
    }

    public OBWriteDomesticResponse links(Links links) {
        this.links = links;
        return this;
    }

    /**
     * Get links
     *
     * @return links
     **/
    @NotNull

    @Valid

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
    }

    public OBWriteDomesticResponse meta(Meta meta) {
        this.meta = meta;
        return this;
    }

    /**
     * Get meta
     *
     * @return meta
     **/
    @NotNull

    @Valid

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OBWriteDomesticResponse obWriteDomesticResponse2 = (OBWriteDomesticResponse) o;
        return Objects.equals(this.data, obWriteDomesticResponse2.data) &&
                Objects.equals(this.links, obWriteDomesticResponse2.links) &&
                Objects.equals(this.meta, obWriteDomesticResponse2.meta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, links, meta);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class OBWriteDomesticResponse2 {\n");

        sb.append("    data: ").append(toIndentedString(data)).append("\n");
        sb.append("    links: ").append(toIndentedString(links)).append("\n");
        sb.append("    meta: ").append(toIndentedString(meta)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}