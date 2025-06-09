import java.sql.*;
import java.util.*;

public class Base_de_Datos {
    public static void main(String[] args) {
        String urlServidor = "jdbc:mysql://172.30.22.39/";
        String user = "pau2";
        String passwd = "1234";
        String nombreBBDD = "FE";

        String urlCompleta = urlServidor + nombreBBDD;

        String createDtbs = "CREATE DATABASE IF NOT EXISTS "+nombreBBDD;

        String table1 =
                """
                CREATE TABLE IF NOT EXISTS objeto (
                id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
                nombre VARCHAR2(30) NOT NULL,
                estado VARCHAR2(40) NOT NULL CHECK IN ('Dañado', 'Bueno'),
                idAula SMALLINT NOT NULL,
                observaciones VARCHAR2(60),
                precioCompra NUMBER(7,2),
                PRIMARY KEY(id),
                FOREIGN KEY(idAula) REFERENCES aula (id));
                """;
        String table2 =
                """
                CREATE TABLE IF NOT EXISTS aula (
                id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
                nombre VARCHAR2(20) NOT NULL,
                piso SMALLINT NOT NULL,
                PRIMARY KEY(id));
                """;
        String table3 =
                """
                CREATE TABLE IF NOT EXISTS empleado (
                id SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT,
                nombre VARCHAR2(30) NOT NULL,
                apellido1 VARCHAR2(30) NOT NULL,
                apellido2 VARCHAR2(30) NOT NULL,
                dni VARCHAR2(9) NOT NULL,
                tipo VARCHAR2(10) NOT NULL CHECK IN ('Profesor','Mantenimiento'),
                PRIMARY KEY (id));
                """;
        String table4 =
                """
                CREATE TABLE IF NOT EXISTS asignado (
                idAula SMALLINT NOT NULL,
                idEmpleado SMALLINT NOT NULL,
                fechaAsignacion DATE,
                FOREIGN KEY (idAula) REFERENCES aula (id),
                FOREIGN KEY (idEmpleado) REFERENCES empleado (id),
                PRIMARY KEY (idAula, idEmpleado));
                """;
        String table5 =
                """
                CREATE TABLE IF NOT EXISTS mantenimiento (
                idEmpleado SMALLINT NOT NULL,
                idObjeto SMALLINT NOT NULL,
                fechaInicio DATE,
                fechaFinal DATE,
                FOREIGN KEY idEmpleado REFERENCES empleado (id),
                FOREIGN KEY idObjeto REFERENCES objeto (id),
                PRIMARY KEY (idEmpleado, idObjeto));
                """;
        String insert1 = "INSERT INTO objeto VALUES ('Ordenador','Dañado', 0, NULL, 30.0), ('Borrador', 'Bueno', 1, NULL, 10.0), ('Tiza', 'Bueno', 2, NULL, 1.0);";

        String insert2 = "INSERT INTO empleado VALUES ('Pau','Collado', 'Navarro', '03152256Z', 'Profesor'), ('Santiago', 'Sánchez', 'March', '26881390F', 'Mantenimiento'), ('Maria', 'Jurado', 'Ibáñez', '03456789Z', 'Profesor');";

        String insert3 = "INSERT INTO aula VALUES ('A01', 0), ('A11', 1), ('A21', 2);";

        String insert4 = "INSERT INTO asignado VALUES (0, 0, NULL), (0, 1, NULL), (0, 2, NULL), (1, 0, NULL), (1, 1, NULL), (1, 2, NULL), (2, 0, NULL), (2, 1, NULL), (2, 2, NULL);";

        String insert5 = "INSERT INTO mantenimiento VALUES (0, 0, NULL, NULL), (0, 1, NULL, NULL), (0, 2, NULL, NULL), (1, 0, NULL, NULL), (1, 1, NULL, NULL), (1, 2, NULL, NULL), (2, 0, NULL, NULL), (2, 1, NULL, NULL), (2, 2, NULL, NULL);";

        try (Connection conexion1 = DriverManager.getConnection(urlCompleta, user, passwd)) {
            System.out.println("Conectando a la base de datos...");
            Statement statement1 = conexion1.createStatement();
            {
                statement1.executeUpdate(createDtbs);
                System.out.println("Base de datos creada con éxito. Creando tablas...");
            }
            try (Statement statement2 = conexion1.createStatement()) {
                {
                    statement2.executeUpdate(table1);
                    statement2.executeUpdate(table2);
                    statement2.executeUpdate(table3);
                    statement2.executeUpdate(table4);
                    statement2.executeUpdate(table5);
                    System.out.println("Tablas creadas. Insertando datos...");
                }
                try (Statement statement3 = conexion1.createStatement()) {
                    {
                        statement3.executeUpdate(insert1);
                        statement3.executeUpdate(insert2);
                        statement3.executeUpdate(insert3);
                        statement3.executeUpdate(insert4);
                        statement3.executeUpdate(insert5);
                    }
                }
                catch (SQLException e) {
                    System.out.println("Error al insertar los datos: "+e.getMessage());
                }
            }
            catch (SQLException e) {
                System.out.println("Error al crear las tablas: "+e.getMessage());
            }
        }
        catch (SQLException e) {
            System.out.println("Error al crear la base de datos: "+e.getMessage());
        }
    }
}