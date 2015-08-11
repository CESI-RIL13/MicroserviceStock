package microservicestock

class Categorie {
    String name

    static hasMany = [familles:Famille]
    static constraints = {
        familles nullable: true
    }
}
