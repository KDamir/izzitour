/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EztourTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CriterionDetailComponent } from '../../../../../../main/webapp/app/entities/criterion/criterion-detail.component';
import { CriterionService } from '../../../../../../main/webapp/app/entities/criterion/criterion.service';
import { Criterion } from '../../../../../../main/webapp/app/entities/criterion/criterion.model';

describe('Component Tests', () => {

    describe('Criterion Management Detail Component', () => {
        let comp: CriterionDetailComponent;
        let fixture: ComponentFixture<CriterionDetailComponent>;
        let service: CriterionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EztourTestModule],
                declarations: [CriterionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CriterionService,
                    JhiEventManager
                ]
            }).overrideTemplate(CriterionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CriterionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CriterionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Criterion(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.criterion).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
