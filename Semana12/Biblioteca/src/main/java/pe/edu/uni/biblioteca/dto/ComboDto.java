package pe.edu.uni.ventapasajes.model;

/**
 *
 * @author Gustavo Coronel
 */
public class ComboDto {
   
   private int id;
   private String etiqueta;

   public ComboDto() {
   }

   public ComboDto(int id, String etiqueta) {
      this.id = id;
      this.etiqueta = etiqueta;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getEtiqueta() {
      return etiqueta;
   }

   public void setEtiqueta(String etiqueta) {
      this.etiqueta = etiqueta;
   }

   @Override
   public String toString() {
      return "" + this.id + " - " + this.etiqueta;
   }
   
   

}
