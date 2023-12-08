import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import {  ReactiveFormsModule } from '@angular/forms';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterTestingModule } from '@angular/router/testing';
import { expect } from '@jest/globals';
import { SessionService } from 'src/app/services/session.service';
import { SessionApiService } from '../../services/session-api.service';
import { FormComponent } from './form.component';
import { sessionMock, sessionServiceMock } from '../../mocked/mocked';
import { ActivatedRoute, Router } from '@angular/router';
import { NgZone } from '@angular/core';
import { of } from 'rxjs';

describe('FormComponent', () => {
  let component: FormComponent;
  let fixture: ComponentFixture<FormComponent>;
  let router: Router;
  let ngZone: NgZone;
  let route: ActivatedRoute;
  let sessionApiService: SessionApiService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({

      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule, 
        MatSnackBarModule,
        MatSelectModule,
        BrowserAnimationsModule
      ],
      providers: [
        { provide: SessionService, useValue: sessionServiceMock },
      ],
      declarations: [FormComponent]
    })
      .compileComponents();
    fixture = TestBed.createComponent(FormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    router = TestBed.inject(Router)
    ngZone = TestBed.inject(NgZone);
    route = TestBed.inject(ActivatedRoute);
    sessionApiService = TestBed.inject(SessionApiService);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('redirected to sessions if you are not admin', () => {
    sessionServiceMock.sessionInformation.admin = false;
    const routerSpy = jest.spyOn(router, 'navigate')

    ngZone.run(() => {
      component.ngOnInit();
      expect(routerSpy).toHaveBeenCalledWith(['/sessions']);
    })
  })

  it('call initForm with session', ()=>{
    jest.spyOn(router, 'url', 'get').mockReturnValue('update');
    jest.spyOn(route.snapshot.paramMap, 'get').mockReturnValue('1');
    const initFormSpy = jest.spyOn(component as any, 'initForm');
    jest.spyOn(sessionApiService, 'detail').mockReturnValue(of(sessionMock));

    ngZone.run(() => {
    component.ngOnInit();

    expect(component.onUpdate).toBe(true);
    expect(initFormSpy).toHaveBeenCalledWith(sessionMock);
    });
  })

  it('call initForm without session', () => {
    const initFormSpy = jest.spyOn(component as any, 'initForm');
    ngZone.run(() => {
    component.ngOnInit();
    expect(initFormSpy).toHaveBeenCalled();
    });
  })


  it('created a session', ()=> {
    component.onUpdate = false;
    const sessionApiServicespy = jest.spyOn(sessionApiService, 'create');;

    component.submit();

    expect(sessionApiServicespy).toBeCalled();
  })

  it('updated a session', ()=> {
    component.onUpdate = true;
    const sessionApiServicespy = jest.spyOn(sessionApiService, 'update');

    component.submit();

    expect(sessionApiServicespy).toBeCalled();
  })
});
