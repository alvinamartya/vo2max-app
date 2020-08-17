<?php
defined('BASEPATH') or exit('No direct script access allowed');
class M_Balke extends CI_Model
{
    function addData($data)
    {
        $this->db->insert('balke', $data);
        return $this->db->affected_rows();
    }

    function getListData($cabor)
    {
        $query = $this->db->query("SELECT * FROM balke b join atlit a ON b.atlitid = a.ID WHERE b.Solusi is null and a.cabang_olahraga = '$cabor'");
        return $query->result_array();
    }

    function getAtlitID($userid)
    {
        $query = $this->db->query("SELECT id FROM atlit WHERE refid = '$userid'");
        return $query->row_array();
    }

    function editData($data, $id)
    {
        $this->db->where('id', $id);
        $this->db->update('balke', $data);
        return $this->db->affected_rows();
    }

    function getData($bulan, $minggu, $atlitid)
    {
        $this->db->where('bulan', $bulan);
        $this->db->where('minggu', $minggu);
        $this->db->where('atlitid', $atlitid);
        $this->db->order_by('umur', 'asc');
        return $this->db->get('balke')->result_array();
    }

    function getDataPelatih($bulan, $minggu)
    {
        $this->db->where('bulan', $bulan);
        $this->db->where('minggu', $minggu);
        $this->db->order_by('umur', 'asc');
        return $this->db->get('balke')->result_array();
    }

    function deleteData($bulan, $atlitid)
    {
        $this->db->where('bulan', $bulan);
        $this->db->where('atlitid', $atlitid);
        $this->db->delete('balke');
        return $this->db->affected_rows();
    }
}
