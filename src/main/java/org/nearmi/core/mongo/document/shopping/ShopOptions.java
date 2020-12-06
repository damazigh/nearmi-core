package org.nearmi.core.mongo.document.shopping;

import lombok.Data;
import org.nearmi.core.mongo.document.enumeration.WeekDays;
import org.nearmi.core.mongo.document.technical.Time;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collection;
import java.util.UUID;

@Document("shopOptions")
@Data
public class ShopOptions {
    @Id
    private String id;
    private boolean automaticOrderConfirmation;
    private boolean manualOrderConfirmation;
    private boolean schedulingAppointment;
    private boolean openWithoutClosure;
    private Collection<WeekDays> openingDays;
    private Time opensAt;
    private Time closesAt;
    // if there is closure store the closure and reopen time
    private Time breakClosureStart;
    private Time breakClosureEnd;

    public ShopOptions() {
        this.id = UUID.randomUUID().toString();
    }
}
