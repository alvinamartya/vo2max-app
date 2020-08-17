<?php
defined('BASEPATH') OR exit('No direct script access allowed');
class M_Beep extends CI_Model
{
    function addData($data)
    {
        $this->db->insert('beep',$data);
        return $this->db->affected_rows();
    }

    function getData($bulan,$minggu,$userid) {
        $this->db->where('bulan',$bulan);
        $this->db->where('minggu',$minggu);
        $this->db->where('userid',$userid);
        $this->db->order_by('umur','asc');
        return $this->db->get('beep')->result_array();
    }

    function deleteData($bulan,$userid) {
        $this->db->where('bulan', $bulan);
        $this->db->where('userid',$userid);
        $this->db->delete('beep');
        return $this->db->affected_rows();
    }
}