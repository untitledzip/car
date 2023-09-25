import React, { useEffect, useState } from 'react';

function CarList(){

    const [cars, setCars] = useState([]);

    useEffect(() => {
        fetch('https://localhost:8080/api/cars')
        .then(response => response.json())
        .then(data => setCars(data._embedded.cars))
        .catch(err => console.error(err));
    }, []);

    return(
        <div>
            <table>
                <body>
                    {
                        cars.map((car, index) =>
                            <tr key={index}>
                                <td>{car.brand}</td>
                                <td>{car.model}</td>
                                <td>{car.color}</td>
                                <td>{car.year}</td>
                                <td>{car.price}</td>
                            </tr>)
                    }
                </body>
            </table>
        </div>
    );
}

export default CarList;