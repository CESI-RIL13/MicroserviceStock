package microservicestock

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FicheController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Fiche.list(params), model:[ficheCount: Fiche.count()]
    }

    def show(Fiche fiche) {
        respond fiche
    }

    def create() {
        respond new Fiche(params)
    }

    @Transactional
    def save(Fiche fiche) {
        if (fiche == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (fiche.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond fiche.errors, view:'create'
            return
        }

        fiche.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'fiche.label', default: 'Fiche'), fiche.id])
                redirect fiche
            }
            '*' { respond fiche, [status: CREATED] }
        }
    }

    def edit(Fiche fiche) {
        respond fiche
    }

    @Transactional
    def update(Fiche fiche) {
        if (fiche == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (fiche.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond fiche.errors, view:'edit'
            return
        }

        fiche.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'fiche.label', default: 'Fiche'), fiche.id])
                redirect fiche
            }
            '*'{ respond fiche, [status: OK] }
        }
    }

    @Transactional
    def delete(Fiche fiche) {

        if (fiche == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        fiche.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'fiche.label', default: 'Fiche'), fiche.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'fiche.label', default: 'Fiche'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
