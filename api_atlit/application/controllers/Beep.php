<?php

use Restserver\Libraries\REST_Controller;

defined('BASEPATH') or exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
/** @noinspection PhpIncludeInspection */
//To Solve File REST_Controller not found
require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Beep extends REST_Controller
{
    function __construct()
    {
        parent::__construct();
        $this->load->database();
        $this->load->model('M_Beep');
    }

    public function deleteAllData_post()
    {
        $bulan      = $this->post('bulan');
        $userid     = $this->post('userid');
        $data       = $this->M_Beep->deleteData($bulan, $userid);
        if ($data > 0) {
            $this->response([
                'status'    =>  TRUE,
                'message'   =>  'success'
            ], REST_CONTROLLER::HTTP_OK);
        } else {
            $this->response([
                'status'    =>  FALSE,
                'message'   =>  'failed'
            ], REST_CONTROLLER::HTTP_OK);
        }
    }

    public function getDataPelatih_post()
    {
        $bulan      = $this->post('bulan');
        $data1      = $this->M_Beep->getDataPelatih($bulan, "1");
        $data2      = $this->M_Beep->getDataPelatih($bulan, "2");
        $data3      = $this->M_Beep->getDataPelatih($bulan, "3");
        $data4      = $this->M_Beep->getDataPelatih($bulan, "4");
        $this->response([
            'status'    =>  TRUE,
            'message'   =>  'success',
            'minggu1'   =>  $data1,
            'minggu2'   =>  $data2,
            'minggu3'   =>  $data3,
            'minggu4'   =>  $data4
        ], REST_CONTROLLER::HTTP_OK);
    }

    public function getListData_post()
    {
        $cabor  = $this->post('cabor');
        $data   = $this->M_Beep->getListData($cabor);
        $this->response($data, REST_CONTROLLER::HTTP_OK);
    }

    public function getData_post()
    {
        $bulan      = $this->post('bulan');
        $userid     = $this->post('userid');

        $getAtlit = $this->M_Beep->getAtlitID($userid);
        $data1      = $this->M_Beep->getData($bulan, "1", $getAtlit["id"]);
        $data2      = $this->M_Beep->getData($bulan, "2", $getAtlit["id"]);
        $data3      = $this->M_Beep->getData($bulan, "3", $getAtlit["id"]);
        $data4      = $this->M_Beep->getData($bulan, "4", $getAtlit["id"]);
        $this->response([
            'status'    =>  TRUE,
            'message'   =>  'success',
            'minggu1'   =>  $data1,
            'minggu2'   =>  $data2,
            'minggu3'   =>  $data3,
            'minggu4'   =>  $data4
        ], REST_CONTROLLER::HTTP_OK);
    }

    public function index_post()
    {
        $bulan      = $this->post('bulan');
        $minggu     = $this->post('minggu');
        $level      = $this->post('level');
        $shutle     = $this->post('shuttle');
        $vo2max     = $this->post('vo2max');
        $kebugaran  = $this->post('tingkat_kebugaran');
        $userid     = $this->post('userid');


        $getAtlit = $this->M_Beep->getAtlitID($userid);
        
        $data = [
            "bulan"             => $bulan,
            "minggu"            => $minggu,
            "level"             => $level,
            "shuttle"            => $shutle,
            "vo2max"            => $vo2max,
            "tingkat_kebugaran" => $kebugaran,
            "atlitid"            => $getAtlit["id"]
        ];

        $post = $this->M_Beep->addData($data);
        if ($post > 0) {
            $this->response([
                'status'    =>  TRUE,
                'message'   =>  'berhasil menambahkan data',
                'data'      =>  $data
            ], REST_CONTROLLER::HTTP_OK);
        } else {
            $this->response([
                'status'    =>  False,
                'message'   =>  'gagal menambahkan data'
            ], REST_CONTROLLER::HTTP_OK);
        }
    }
}
