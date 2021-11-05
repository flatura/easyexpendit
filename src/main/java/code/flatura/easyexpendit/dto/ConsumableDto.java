package code.flatura.easyexpendit.dto;

import code.flatura.easyexpendit.model.Consumable;
import code.flatura.easyexpendit.model.Status;

public class ConsumableDto {
    private String id;
    private String name;
    private String contract;
    private Integer price;
    private String partNumber;
    private Integer categoryId;
    private Status status;

    public ConsumableDto(String id, String name, String contract, Integer price, String partNumber, Integer category, Status status) {
        this.id = id;
        this.name = name;
        this.contract = contract;
        this.price = price;
        this.partNumber = partNumber;
        this.categoryId = category;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static ConsumableDto convertFrom(Consumable c) {
        return new ConsumableDto(
                c.getId().toString(),
                c.getName(),
                c.getContract(),
                c.getPrice(),
                c.getPartNumber(),
                c.getCategory().getId(),
                c.getStatus());
    }
}
