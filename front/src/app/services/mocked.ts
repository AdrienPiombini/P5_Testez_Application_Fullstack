import { Teacher } from "../interfaces/teacher.interface";
import { User } from "../interfaces/user.interface";

export const teacherMock: Teacher[] =[
    {
        id: 1,
        lastName: 'Doe',
        firstName: 'John',
        createdAt: new Date(),
        updatedAt: new Date()
    },

    {
        id: 2,
        lastName: 'Foo',
        firstName: 'Bar',
        createdAt: new Date(),
        updatedAt: new Date()
    },

    {
        id: 3,
        lastName: 'Barro',
        firstName: 'Fooro',
        createdAt: new Date(),
        updatedAt: new Date()
    },

]

export const userMock: User = {
    id: 1,
    email: 'hello@gmail.com',
    lastName: 'foo',
    firstName: 'bar',
    admin: false,
    password: 'secret',
    createdAt: new Date(),
    updatedAt: new Date(),
}