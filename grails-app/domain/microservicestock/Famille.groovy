package microservicestock

class Famille {
    String nom

    static belongsTo = [parent:Famille,categorie:Categorie]
    static hasMany = [enfants:Famille]

    static constraints = {
        enfants nullable: true
        parent nullable: true
    }
}
