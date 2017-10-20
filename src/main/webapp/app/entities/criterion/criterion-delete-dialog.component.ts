import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Criterion } from './criterion.model';
import { CriterionPopupService } from './criterion-popup.service';
import { CriterionService } from './criterion.service';

@Component({
    selector: 'jhi-criterion-delete-dialog',
    templateUrl: './criterion-delete-dialog.component.html'
})
export class CriterionDeleteDialogComponent {

    criterion: Criterion;

    constructor(
        private criterionService: CriterionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.criterionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'criterionListModification',
                content: 'Deleted an criterion'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-criterion-delete-popup',
    template: ''
})
export class CriterionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private criterionPopupService: CriterionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.criterionPopupService
                .open(CriterionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
