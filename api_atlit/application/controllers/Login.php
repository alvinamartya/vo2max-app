<?php

use Restserver\Libraries\REST_Controller;

defined('BASEPATH') or exit('No direct script access allowed');

// This can be removed if you use __autoload() in config.php OR use Modular Extensions
/** @noinspection PhpIncludeInspection */
//To Solve File REST_Controller not found
require APPPATH . 'libraries/REST_Controller.php';
require APPPATH . 'libraries/Format.php';

class Login extends REST_Controller
{
    function __construct()
    {
        parent::__construct();
        $this->load->database();
        $this->load->model('M_Login');
        $this->load->helper('string');
    }

    public function login_post()
    {
        $username = $this->post('username');
        $password = $this->post('password');
        if (!isset($username) || !isset($password)) {
            $this->response([
                'status'    =>  False,
                'message'   =>  'pastikan data username atau password telah diisi'
            ], REST_CONTROLLER::HTTP_OK);
        } else {
            $salt = $this->M_Login->getSalt($username);
            if ($salt) {
                $enk_password = $this->checkHasSSHA($salt['salt'], $password);
                $data = $this->M_Login->getLogin($username, $enk_password);
                if ($data) {
                    $cabor = $this->M_Login->getCabor($data["akses"], $data["id"]);
                    $resut = [
                        'id'        => $data['id'],
                        'username'  => $data['username'],
                        'akses'     => $data['akses'],
                        'cabor'     => $cabor['cabang_olahraga'],
                        'status'    => $data['status']
                    ];

                    $this->response([
                        'status'    =>  TRUE,
                        'message'   =>  'anda berhasil login',
                        'data'      =>  $resut
                    ], REST_CONTROLLER::HTTP_OK);
                } else {
                    $this->response([
                        'status'    =>  False,
                        'message'   =>  'password anda salah'
                    ], REST_CONTROLLER::HTTP_OK);
                }
            } else {
                $this->response([
                    'status'    =>  False,
                    'message'   =>  'username anda salah'
                ], REST_CONTROLLER::HTTP_OK);
            }
        }
    }

    public function index_post()
    {
        $username   = $this->post('username');
        $password   = $this->post('password');
        $akses      = $this->post('akses');
        $uuid       = uniqid('', true);
        $hash       = $this->hashSSHA($password);
        $encrypted  = $hash['encrypted'];
        $salt       = $hash['salt'];

        $data = [
            'username'      => $username,
            'unik_id'       => $uuid,
            'enk_password'  => $encrypted,
            'salt'          => $salt,
            'akses'         => $akses,
            'status'        => 'active',
            'tgl_dibuat'    => date('Y-m-d H:i:s')
        ];

        $cek = $this->M_Login->checkUsername($this->post('username'));
        if ($cek) {
            $this->response([
                'status'    => false,
                'message'   => 'username sudah ada'
            ], REST_Controller::HTTP_OK);
        } else {
            $post_data = $this->M_Login->addLogin($data);
            if ($post_data > 0) {
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

    public function changePassword_post()
    {
        $cek = $this->M_Login->checkUsername($this->post('username'));
        if ($cek) {
            $username   = $this->post('username');
            $password   = $this->post('password');
            $hash       = $this->hashSSHA($password);
            $encrypted  = $hash['encrypted'];
            $salt       = $hash['salt'];

            $data = [
                'enk_password'  => $encrypted,
                'salt'          => $salt
            ];

            $post_data = $this->M_Login->editLogin($data, $username);
            if ($post_data > 0) {
                $this->response([
                    'status'    =>  TRUE,
                    'message'   =>  'berhasil mengubah data'
                ], REST_CONTROLLER::HTTP_OK);
            } else {
                $this->response([
                    'status'    =>  False,
                    'message'   =>  'gagal mengubah data'
                ], REST_CONTROLLER::HTTP_OK);
            }
        } else {
            $this->response([
                'status'    => false,
                'message'   => 'data tidak ditemukan'
            ], REST_Controller::HTTP_OK);
        }
    }

    public function hashSSHA($password)
    {
        $salt = random_string('sha1');
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = [
            'salt' => $salt,
            'encrypted' => $encrypted
        ];
        return $hash;
    }

    public function checkHasSSHA($salt, $password)
    {
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        return $encrypted;
    }
}
