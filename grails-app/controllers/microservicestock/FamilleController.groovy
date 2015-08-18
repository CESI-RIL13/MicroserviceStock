package microservicestock

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FamilleController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Famille.list(params), model:[familleCount: Famille.count()]
    }

    def show(Famille famille) {
        respond famille
    }

    def create() {
        respond new Famille(params)
    }

    @Transactional
    def save(Famille famille) {

        if (famille == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (famille.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond famille.errors, view:'create'
            return
        }

        famille.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'famille.label', default: 'Famille'), famille.id])
                redirect famille
            }
            '*' { respond famille, [status: CREATED] }
        }
    }

    def edit(Famille famille) {
        respond famille
    }

    @Transactional
    def update(Famille famille) {
        if (famille == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (famille.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond famille.errors, view:'edit'
            return
        }

        famille.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'famille.label', default: 'Famille'), famille.id])
                redirect famille
            }
            '*'{ respond famille, [status: OK] }
        }
    }

    @Transactional
    def delete(Famille famille) {

        if (famille == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        famille.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'famille.label', default: 'Famille'), famille.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'famille.label', default: 'Famille'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
