import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HipsterAcademyTestModule } from '../../../test.module';
import { ExaminationDetailComponent } from 'app/entities/examination/examination-detail.component';
import { Examination } from 'app/shared/model/examination.model';

describe('Component Tests', () => {
  describe('Examination Management Detail Component', () => {
    let comp: ExaminationDetailComponent;
    let fixture: ComponentFixture<ExaminationDetailComponent>;
    const route = ({ data: of({ examination: new Examination(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HipsterAcademyTestModule],
        declarations: [ExaminationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ExaminationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ExaminationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load examination on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.examination).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
