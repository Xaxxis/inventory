package id.xaxxis.inventory.entity.inventory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.xaxxis.inventory.entity.master.item.MasterItem;
import id.xaxxis.inventory.entity.master.location.MasterLocation;
import id.xaxxis.inventory.entity.master.location.Outlet;
import id.xaxxis.inventory.entity.master.suplier.Suplier;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Entity
@Table(name = "inventory")
@Data
@EqualsAndHashCode
public class Inventory {

   @Id
   @GenericGenerator(name = "uuid", strategy = "uuid")
   @GeneratedValue(generator = "uuid")
   @Column(name = "inv_id", length = 100)
   private String invId;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private MasterItem masterItem;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "address"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="location_d")
    private MasterLocation masterLocation;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "masterLocation"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="outlet_id")
    private Outlet outlet;

    @JsonManagedReference
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "masterLocation"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "suplier_id")
    private Suplier suplier;

    @Column(name = "cost_price", length = 8)
    @DecimalMin(value = "0.00", message = "*Nominal harga beli tidak boleh negatif")
    private BigDecimal costPrice;

    @Column(name = "sell_price", length = 8)
    @DecimalMin(value = "0.00", message = "*Nominal harga jual tidak boleh negatif")
    private BigDecimal sellPrice;

    @Column(name = "on_stock", length = 5, nullable = false)
    @Min(value = 0, message = "*Stok tidak boleh angka negatif")
    private Integer stock;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    public Inventory() {
    }

    public Inventory(MasterItem masterItem, MasterLocation masterLocation, Outlet outlet, Suplier suplier, @DecimalMin(value = "0.00", message = "*Nominal harga beli tidak boleh negatif") BigDecimal costPrice, @DecimalMin(value = "0.00", message = "*Nominal harga jual tidak boleh negatif") BigDecimal sellPrice, @Min(value = 0, message = "*Stok tidak boleh angka negatif") Integer stock, Boolean isActive) {
        this.masterItem = masterItem;
        this.masterLocation = masterLocation;
        this.outlet = outlet;
        this.suplier = suplier;
        this.costPrice = costPrice;
        this.sellPrice = sellPrice;
        this.stock = stock;
        this.isActive = isActive;
    }
}
