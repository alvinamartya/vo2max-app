<?php

use Restserver\Libraries\REST_Controller;

defined('BASEPATH') or exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
/** @noinspection PhpIncludeInspection */
//To Solve File REST_Controller not found
require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Cooper extends REST_Controller
{
    function __construct()
    {
        parent::__construct();
        $this->load->database();
        $this->load->model('M_Cooper');
    }

    public function deleteAllData_post()
    {
        $bulan      = $this->post('bulan');
        $atlitid    = $this->post('atlitid');
        $data       = $this->M_Cooper->deleteData($bulan, $atlitid);
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

    public function getListData_post()
    {
        $cabor      = $this->post('cabor');
        $data       = $this->M_Cooper->getListData($cabor);
        $this->response($data, REST_CONTROLLER::HTTP_OK);
    }

    public function setSolusi_post()
    {
        $id        = $this->post('id');
        $solusi = $this->post('solusi');

        $data = [
            'Solusi' => $solusi
        ];

        $post_data = $this->M_Cooper->editData($data, $id);
        if ($post_data > 0) {
            $this->response([
                'status'    =>  TRUE,
                'message'   =>  'berhasil menambah solusi'
            ], REST_CONTROLLER::HTTP_OK);
        } else {
            $this->response([
                'status'    =>  False,
                'message'   =>  'gagal menambah solusi'
            ], REST_CONTROLLER::HTTP_OK);
        }
    }

    public function getDataPelatih_post()
    {
        $bulan      = $this->post('bulan');
        $data1      = $this->M_Cooper->getDataPelatih($bulan, "1");
        $data2      = $this->M_Cooper->getDataPelatih($bulan, "2");
        $data3      = $this->M_Cooper->getDataPelatih($bulan, "3");
        $data4      = $this->M_Cooper->getDataPelatih($bulan, "4");
        $this->response([
            'status'    =>  TRUE,
            'message'   =>  'success',
            'minggu1'   =>  $data1,
            'minggu2'   =>  $data2,
            'minggu3'   =>  $data3,
            'minggu4'   =>  $data4
        ], REST_CONTROLLER::HTTP_OK);
    }

    public function getData_post()
    {
        $bulan      = $this->post('bulan');
        $atlitid    = $this->post('atlitid');

        $data1      = $this->M_Cooper->getData($bulan, "1", $atlitid);
        $data2      = $this->M_Cooper->getData($bulan, "2", $atlitid);
        $data3      = $this->M_Cooper->getData($bulan, "3", $atlitid);
        $data4      = $this->M_Cooper->getData($bulan, "4", $atlitid);
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
        $waktu      = $this->post('waktu');
        $vo2max     = $this->post('vo2max');
        $kebugaran  = $this->post('tingkat_kebugaran');
        $userid     = $this->post('userid');

        $getAtlit = $this->M_Cooper->getAtlitID($userid);

        $data = [
            "bulan"             => $bulan,
            "minggu"            => $minggu,
            "vo2max"            => $vo2max,
            "tingkat_kebugaran" => $kebugaran,
            "waktu"             => $waktu,
            "atlitid"           => $getAtlit["id"]
        ];

        $post = $this->M_Cooper->addData($data);
        if($post > 0)
        {
            $this->response([
                'status'    =>  TRUE,
                'message'   =>  'berhasil menambahkan data',
                'data'      =>  $data
            ], REST_CONTROLLER::HTTP_OK);
        }
        else
        {
            $this->response([
                'status'    =>  False,
                'message'   =>  'gagal menambahkan data'
            ], REST_CONTROLLER::HTTP_OK);
        }
    }
}
