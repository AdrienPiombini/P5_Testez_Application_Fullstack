import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { UserService } from './user.service';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { userMock } from './mocked';

describe('UserService', () => {
  let service: UserService;
  let httpControler: HttpTestingController

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule,
        HttpClientTestingModule
      ]
    });
    service = TestBed.inject(UserService);
    httpControler = TestBed.inject(HttpTestingController)
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('get a user', () => {
    service.getById('1').subscribe(user => {
      expect(user).toBe(userMock);
    })

    const mockReq = httpControler.expectOne('api/user/1');
    expect(mockReq.request.method).toBe('GET');
    mockReq.flush(userMock);
  })

  it('delete and return any', () => {
    service.delete('1').subscribe((response) => {
      expect(response).toBe(true);
    })
    const mockReq = httpControler.expectOne('api/user/1');
    expect(mockReq.request.method).toBe('DELETE');
    mockReq.flush(true)
  })
});
