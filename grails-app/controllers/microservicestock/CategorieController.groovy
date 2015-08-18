package microservicestock

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class CategorieController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Categorie.list(params), model:[categorieCount: Categorie.count()]
    }

    def show(Categorie categorie) {
        respond categorie
    }

    def create() {
        respond new Categorie(params)
    }

    @Transactional
    def save(Categorie categorie) {
        if (categorie == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (categorie.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond categorie.errors, view:'create'
            return
        }

        categorie.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'categorie.label', default: 'Categorie'), categorie.id])
                redirect categorie
            }
            '*' { respond categorie, [status: CREATED] }
        }
    }

    def edit(Categorie categorie) {
        respond categorie
    }

    @Transactional
    def update(Categorie categorie) {
        if (categorie == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (categorie.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond categorie.errors, view:'edit'
            return
        }

        categorie.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'categorie.label', default: 'Categorie'), categorie.id])
                redirect categorie
            }
            '*'{ respond categorie, [status: OK] }
        }
    }

    @Transactional
    def delete(Categorie categorie) {

        if (categorie == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        categorie.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'categorie.label', default: 'Categorie'), categorie.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'categorie.label', default: 'Categorie'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
