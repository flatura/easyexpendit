package code.flatura.easyexpendit.dto;

import code.flatura.easyexpendit.model.Consumable;

public class ConsumableDto {
    private String id;
    private String name;
    private String contract;
    private Integer price;
    private String partNumber;
    private Integer categoryId;
    private String status;
    private Integer count;
    private String comment;

    public ConsumableDto(String id, String name, String contract, Integer price, String partNumber, Integer categoryId, String status, Integer count, String comment) {
        this.id = id;
        this.name = name;
        this.contract = contract;
        this.price = price;
        this.partNumber = partNumber;
        this.categoryId = categoryId;
        this.status = status;
        this.count = count;
        this.comment = comment;
    }

    public ConsumableDto(String name, String contract, Integer price, String partNumber, Integer categoryId, String status, Integer count, String comment) {
        this.name = name;
        this.contract = contract;
        this.price = price;
        this.partNumber = partNumber;
        this.categoryId = categoryId;
        this.status = status;
        this.count = count;
        this.comment = comment;
    }

    public ConsumableDto() {
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
                c.getStatus().name(),
                0,
                c.getComment());
    }
}
