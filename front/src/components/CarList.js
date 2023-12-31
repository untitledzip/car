import React, { useEffect, useState } from 'react';
import { SERVER_URL } from '../constants.js';
import { DataGrid, GridToolbarContainer, GridToolbarExport, gridClasses } from '@mui/x-data-grid';
import { Snackbar } from '@material-ui/core';
import AddCar from './AddCar.js';
import EditCar from './EditCar.js';
import Stack from '@mui/material/Stack';
import IconButton from '@mui/material/IconButton';
import DeleteIcon from '@mui/icons-material/Delete';

/*
    @mui/x-data-grid
     - 데이터 표 컴포넌트를 이용하면 표에 필요한 모든 기능(ex. 정렬, 필터링, 페이지 매김)을 아주 적은 코드로 구현할 수 있음

    renderCell
     - 셀에 더 복잡한 내용을 넣어야 할 때 사용함, 셀의 내용이 렌더링되는 방법을 정의함

    Snackbar 컴포넌트
     - 삭제 결과를 보여주는 알림 메시지 구현

    데이터를 CSV로 내보내는 기능
     - import{ GridToolbarContainer, GridToolbarExport, gridClasses } from '@mui/x-data-grid

    MUI에는 미리 제작된 SVG 아이콘이 있음
     - @mui/icons-material
*/

function CarList(){

        const fetchCars = () => {

        //세션 저장소에서 토큰을 읽고 Authorization 헤더에 이를 포함
        const token = sessionStorage.getItem("jwt");

        fetch(SERVER_URL + 'api/cars', {
            headers: {'Authorization': token}
        })
        .then(response => response.json)
        .then(data => setCars(data._embedded.cars))
        .catch(err => console.error(err));
    }

    const [cars, setCars] = useState([]);

    const columns = [
        {field: 'brand', headerName: 'Brand', width: 200},
        {field: 'model', headerName: 'Model', width: 200},
        {field: 'color', headerName: 'Color', width: 200},
        {field: 'year', headerName: 'Year', width: 150},
        {field: 'price', headerName: 'Price', width: 150},
        {
            field: '_links.car.href',
            headerName: '',
            sortable: false,
            filterable: false,
            renderCell: row =>
                <EditCar
                    data={row}
                    updateCar={updateCar} />
        },
        {
            field: '_links_self.href',
            headerName: '',
            sortable: false,
            filterable: false,
            renderCell: row =>
                <IconButton onClick={() => onDelClick(row.id)}>
                    <DeleteIcon color="error" />
                </IconButton>
        }
    ];

    useEffect(() => {
        //constants.js에서 정의한 SERVER_URL 사용
        fetch(SERVER_URL + 'api/cars')
        .then(response => response.json())
        .then(data => setCars(data._embedded.cars))
        .catch(err => console.error(err));
    }, []);

    //Snackbar 컴포넌트의 표시 여부 처리를 하기 위해 open이란 상태를 선언
    const [open, setOpen] = useState(false);

    //useEffect 후크에서 fetch 메서드 가져오기
    //자동차를 삭제한 후 업데이트된 자동차 목록을 사용자에게 보여주려면 fetch를 호출해야 함
    useEffect(() => {
        fetchCars();
    }, []);

    //삭제 기능
    const onDelClick = (url) => {
        if(window.confirm("Are you sure to delete?")){
            const token = sessionStorage.getItem("jwt");
            fetch(url, {method: 'DELETE',
                        headers: {'Authorization':token}
            })
            .then(response => {
                if(response.ok){
                    fetchCars();
                    setOpen(true);
                } else {
                    alert('Something went wrong!');
                }
            })
            .catch(err => console.error(err))
        }
    }

    <Snackbar
        open={open}
        autoHideDuration={1000} //자동으로 onClose 함수가 호출됨, 메시지가 사라지는 시간을 밀리초 단위로 정의
        onClose={() => setOpen(false)}
        message="Car deleted"
    />

    //자동차 추가
    const addCar = (car) =>{
        const token = sessionStorage.getItem("jwt")
        fetch(SERVER_URL + 'api/cars',
            {
                method: 'POST',
                headers: {'Content-Type':'application/json',
                            'Authorization':token
                },
                body: JSON.stringify(car)
            })
        .then(response => {
            if(response.ok){
                fetchCars();
            } else {
                alert('Something went wrong');
            }
        })
        .catch(err => console.error(err))
    }

    //자동차 업데이트
    const updateCar = (car, link) => {
        const token = sessionStorage.getItem("jwt")
        fetch(link,
            {
                method: 'PUT',
                headers: {'Content-Type':'application/json',
                            'Authorization':token
                         },
                body: JSON.stringify(car)
            })
        .then(response => {
            if(response.ok){
                fetchCars();
            } else {
                alert('Something went wrong');
            }
        })
        .catch(err => console.error(err))
    }

    return(

        <React.Fragment>
            <Stack mt={2} mb={2}>
                <AddCar addCar={addCar} />
            </Stack>
            <div style = {{ height: 500, width: '100%' }}>
                <DataGrid
                    rows={cars}
                    columns={columns}
                    disableSelectionOnClick={true} //true로 설정하면 동작을 비활성화 할 수 있음
                    getRowId={row => row._links.self.href}
                    components={{ Toolbar: CustomToolbar }}/>
            </div>
        </React.Fragment>
    );
}
//            <table>
//                <tbody>
//                    {
//                        cars.map((car, index) =>
//                            <tr key={index}>
//                                <td>{car.brand}</td>
//                                <td>{car.model}</td>
//                                <td>{car.color}</td>
//                                <td>{car.year}</td>
//                                <td>{car.price}</td>
//                            </tr>
//                        )
//                    }
//                </tbody>
//            </table>

function CustomToolbar(){
    return(
        <GridToolbarContainer
            className={gridClasses.toolbarContainer}>
            <GridToolbarExport />
        </GridToolbarContainer>
    )
}

export default CarList;