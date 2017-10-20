import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Criterion } from './criterion.model';
import { CriterionPopupService } from './criterion-popup.service';
import { CriterionService } from './criterion.service';
import { Filter, FilterService } from '../filter';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-criterion-dialog',
    templateUrl: './criterion-dialog.component.html'
})
export class CriterionDialogComponent implements OnInit {

    criterion: Criterion;
    isSaving: boolean;

    filters: Filter[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private criterionService: CriterionService,
        private filterService: FilterService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.filterService.query()
            .subscribe((res: ResponseWrapper) => { this.filters = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.criterion.id !== undefined) {
            this.subscribeToSaveResponse(
                this.criterionService.update(this.criterion));
        } else {
            this.subscribeToSaveResponse(
                this.criterionService.create(this.criterion));
        }
    }

    private subscribeToSaveResponse(result: Observable<Criterion>) {
        result.subscribe((res: Criterion) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Criterion) {
        this.eventManager.broadcast({ name: 'criterionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackFilterById(index: number, item: Filter) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-criterion-popup',
    template: ''
})
export class CriterionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private criterionPopupService: CriterionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.criterionPopupService
                    .open(CriterionDialogComponent as Component, params['id']);
            } else {
                this.criterionPopupService
                    .open(CriterionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
