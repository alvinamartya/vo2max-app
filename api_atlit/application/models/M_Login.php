<?php
defined('BASEPATH') or exit('No direct script access allowed');
class M_Login extends CI_Model
{
    function getCabor($akses, $id)
    {
        if ($akses == "pelatih") {
            $this->db->select('cabang_olahraga');
            $this->db->where('refid', $id);
            return $this->db->get('pelatih')->row_array();
        } else {
            $this->db->select('cabang_olahraga');
            $this->db->where('refid', $id);
            return $this->db->get('atlit')->row_array();
        }
    }

    function getLogin($username, $password)
    {
        $this->db->select('id,username,akses,status');
        $this->db->where('username', $username);
        $this->db->where('enk_password', $password);
        return $this->db->get('userlogin')->row_array();
    }

    function addLogin($data)
    {
        $this->db->insert('userlogin', $data);
        return $this->db->affected_rows();
    }

    function editLogin($data, $id)
    {
        $this->db->where('username', $id);
        $this->db->update('userlogin', $data);
        return $this->db->affected_rows();
    }

    function deleteLogin($id)
    {
        $this->db->where('username', $id);
        $this->db->delete('userlogin');
        return $this->db->affected_rows();
    }

    function getSalt($username)
    {
        $this->db->where('username', $username);
        $this->db->select('salt');
        return $this->db->get('userlogin')->row_array();
    }

    function checkUsername($username)
    {
        $this->db->where('username', $username);
        $this->db->select('username');
        return $this->db->get('userlogin')->result_array();
    }
}
