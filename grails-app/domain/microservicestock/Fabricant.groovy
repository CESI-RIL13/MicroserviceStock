package microservicestock

class Fabricant {

    String name

    static hasMany = [fiches:Fiche]

    static constraints = {
    }
}
