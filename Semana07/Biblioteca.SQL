-- Crear la base de datos
CREATE DATABASE BibliotecaUniversitaria;
GO

-- Usar la base de datos recién creada
USE BibliotecaUniversitaria;
GO

-- Tabla de Usuarios
CREATE TABLE Usuarios (
    UsuarioID INT PRIMARY KEY IDENTITY(1,1),
    Nombre NVARCHAR(100) NOT NULL,
    Apellido NVARCHAR(100) NOT NULL,
    Correo NVARCHAR(100) NOT NULL UNIQUE,
    Rol NVARCHAR(50) CHECK (Rol IN ('Estudiante', 'Profesor', 'Administrativo', 'Administrador')),
    FechaRegistro DATETIME DEFAULT GETDATE()
);
GO

-- Tabla de Libros
CREATE TABLE Libros (
    LibroID INT PRIMARY KEY IDENTITY(1,1),
    Titulo NVARCHAR(200) NOT NULL,
    Autor NVARCHAR(100) NOT NULL,
    Categoria NVARCHAR(100),
    AnioPublicacion INT,
    Disponible BIT DEFAULT 1,
    FechaRegistro DATETIME DEFAULT GETDATE()
);
GO

-- Tabla de Préstamos
CREATE TABLE Prestamos (
    PrestamoID INT PRIMARY KEY IDENTITY(1,1),
    UsuarioID INT NOT NULL FOREIGN KEY REFERENCES Usuarios(UsuarioID),
    LibroID INT NOT NULL FOREIGN KEY REFERENCES Libros(LibroID),
    FechaPrestamo DATETIME DEFAULT GETDATE(),
    FechaDevolucion DATETIME,
    FechaLimite DATETIME NOT NULL,
    Devuelto BIT DEFAULT 0
);
GO

-- Tabla de Devoluciones
CREATE TABLE Devoluciones (
    DevolucionID INT PRIMARY KEY IDENTITY(1,1),
    PrestamoID INT NOT NULL FOREIGN KEY REFERENCES Prestamos(PrestamoID),
    FechaDevolucion DATETIME DEFAULT GETDATE(),
    Multa DECIMAL(10,2) DEFAULT 0.00
);
GO

-- Tabla de Multas
CREATE TABLE Multas (
    MultaID INT PRIMARY KEY IDENTITY(1,1),
    UsuarioID INT NOT NULL FOREIGN KEY REFERENCES Usuarios(UsuarioID),
    Monto DECIMAL(10,2) NOT NULL,
    FechaMulta DATETIME DEFAULT GETDATE(),
    Pagada BIT DEFAULT 0
);
GO

-- Insertar datos de prueba en Usuarios
INSERT INTO Usuarios (Nombre, Apellido, Correo, Rol)
VALUES 
('Juan', 'Pérez', 'juan.perez@universidad.edu', 'Estudiante'),
('Ana', 'Gómez', 'ana.gomez@universidad.edu', 'Profesor'),
('Carlos', 'Rodríguez', 'carlos.rodriguez@universidad.edu', 'Administrativo'),
('Marta', 'López', 'marta.lopez@universidad.edu', 'Administrador');
GO

-- Insertar datos de prueba en Libros
INSERT INTO Libros (Titulo, Autor, Categoria, AnioPublicacion)
VALUES 
('Introducción a la Programación', 'John Doe', 'Tecnología', 2015),
('Cálculo Avanzado', 'Jane Smith', 'Matemáticas', 2010),
('Historia del Arte', 'Carlos Fernández', 'Arte', 2008),
('Inteligencia Artificial', 'Alan Turing', 'Ciencia', 2020);
GO

-- Insertar datos de prueba en Prestamos (prestamos ficticios)
INSERT INTO Prestamos (UsuarioID, LibroID, FechaLimite)
VALUES 
(1, 1, DATEADD(DAY, 14, GETDATE())), -- Juan Pérez toma "Introducción a la Programación"
(2, 2, DATEADD(DAY, 14, GETDATE())), -- Ana Gómez toma "Cálculo Avanzado"
(3, 3, DATEADD(DAY, 14, GETDATE())), -- Carlos Rodríguez toma "Historia del Arte"
(1, 4, DATEADD(DAY, 14, GETDATE())); -- Juan Pérez toma "Inteligencia Artificial"
GO

-- Insertar datos de prueba en Devoluciones (devoluciones ficticias)
INSERT INTO Devoluciones (PrestamoID, FechaDevolucion, Multa)
VALUES 
(1, GETDATE(), 0.00), -- Juan Pérez devuelve "Introducción a la Programación" sin multa
(2, DATEADD(DAY, 5, GETDATE()), 2.50); -- Ana Gómez devuelve tarde "Cálculo Avanzado" con multa
GO

-- Insertar datos de prueba en Multas (multas ficticias)
INSERT INTO Multas (UsuarioID, Monto)
VALUES 
(2, 2.50); -- Ana Gómez tiene una multa pendiente
GO
