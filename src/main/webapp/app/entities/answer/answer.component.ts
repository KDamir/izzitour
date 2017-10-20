import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';

import { Answer } from './answer.model';
import { AnswerService } from './answer.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-answer',
    templateUrl: './answer.component.html'
})
export class AnswerComponent implements OnInit, OnDestroy {
answers: Answer[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private answerService: AnswerService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.answerService.query().subscribe(
            (res: ResponseWrapper) => {
                this.answers = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInAnswers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Answer) {
        return item.id;
    }
    registerChangeInAnswers() {
        this.eventSubscriber = this.eventManager.subscribe('answerListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
