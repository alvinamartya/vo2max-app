<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class M_Beep extends CI_Model
{
    function addData($data)
    {
        $this->db->insert('beep',$data);
        return $this->db->affected_rows();
    }

    function getListData($cabor)
    {
        $query = $this->db->query("SELECT b.id,nama,vo2max,tingkat_kebugaran,bulan,minggu,shuttle,`level` FROM beep b join atlit a ON b.atlitid = a.ID WHERE b.Solusi is null and a.cabang_olahraga = '$cabor'");
        return $query->result_array();
    }

    function getData($bulan,$minggu,$atlitid) {
        $this->db->where('bulan',$bulan);
        $this->db->where('minggu',$minggu);
        $this->db->where('atlitid',$atlitid);
        return $this->db->get('beep')->result_array();
    }

    function getDataPelatih($bulan,$minggu) {
        $this->db->where('bulan',$bulan);
        $this->db->where('minggu',$minggu);
        return $this->db->get('beep')->result_array();
    }

    function getAtlitID($userid)
    {
        $query = $this->db->query("SELECT id FROM atlit WHERE refid = '$userid'");
        return $query->row_array();
    }

    function deleteData($bulan,$atlitid) {
        $this->db->where('bulan', $bulan);
        $this->db->where('atlitid',$atlitid);
        $this->db->delete('beep');
        return $this->db->affected_rows();
    }

    function editData($data, $id)
    {
        $this->db->where('id', $id);
        $this->db->update('beep', $data);
        return $this->db->affected_rows();
    }

}