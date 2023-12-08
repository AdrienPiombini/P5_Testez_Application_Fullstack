import { HttpClientModule } from '@angular/common/http';
import { TestBed, fakeAsync, tick } from '@angular/core/testing';
import { expect } from '@jest/globals';
import { TeacherService } from './teacher.service';
import { 
  HttpClientTestingModule, 
  HttpTestingController 
} from '@angular/common/http/testing';
import { Teacher } from '../interfaces/teacher.interface';
import { teacherMock } from './mocked';


describe('TeacherService', () => {
  let service: TeacherService;
  let httpControler: HttpTestingController

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule,
        HttpClientTestingModule,
      ]
    });
    service = TestBed.inject(TeacherService);
    httpControler = TestBed.inject(HttpTestingController)
  });

  afterEach(() => {
    httpControler.verify();
  })

  it('should be created', () => {
    expect(service).toBeTruthy();
  });


  it('get all teachers', () => {

    service.all().subscribe((teachers) => {
      expect(teachers).toEqual(teacherMock);
      expect(teachers.length).toBe(teacherMock.length);
    })
    const mockReq = httpControler.expectOne('api/teacher');
    mockReq.flush(Object.values(teacherMock))
    expect(mockReq.request.method).toBe('GET');
  })

  it('get one teacher', () => {
    service.detail('2').subscribe((teacher) => {
      expect(teacher).toBe(teacherMock[2]);
      expect(teacher.firstName).toBe('Fooro')
    })
    const mockReq = httpControler.expectOne('api/teacher/2');
    expect(mockReq.request.method).toBe('GET');
    mockReq.flush(teacherMock[2])  
  })

});
