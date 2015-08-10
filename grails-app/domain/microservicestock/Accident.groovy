package microservicestock

class Accident {
    Date date
    Long ref_trajet
    String annotation

    static belongsTo = [produit:Produit]
    static constraints = {
    }
}
