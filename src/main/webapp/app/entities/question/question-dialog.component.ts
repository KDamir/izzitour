import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Question } from './question.model';
import { QuestionPopupService } from './question-popup.service';
import { QuestionService } from './question.service';

@Component({
    selector: 'jhi-question-dialog',
    templateUrl: './question-dialog.component.html'
})
export class QuestionDialogComponent implements OnInit {

    question: Question;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private questionService: QuestionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.question.id !== undefined) {
            this.subscribeToSaveResponse(
                this.questionService.update(this.question));
        } else {
            this.subscribeToSaveResponse(
                this.questionService.create(this.question));
        }
    }

    private subscribeToSaveResponse(result: Observable<Question>) {
        result.subscribe((res: Question) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Question) {
        this.eventManager.broadcast({ name: 'questionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-question-popup',
    template: ''
})
export class QuestionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private questionPopupService: QuestionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.questionPopupService
                    .open(QuestionDialogComponent as Component, params['id']);
            } else {
                this.questionPopupService
                    .open(QuestionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
