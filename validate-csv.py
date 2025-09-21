#!/usr/bin/env python3
"""
CSV Validation Script for Power BI Compatibility
Validates the generated CSV file for Power BI import requirements
"""

import csv
import os
import sys
from datetime import datetime

def validate_csv_file(file_path):
    """Validates CSV file for Power BI compatibility"""
    print(f"=== Validating CSV File: {file_path} ===")
    
    if not os.path.exists(file_path):
        print(f"❌ ERROR: File not found: {file_path}")
        return False
    
    try:
        with open(file_path, 'r', encoding='utf-8-sig') as file:
            # Check for BOM (Byte Order Mark)
            if file.read(1) == '\ufeff':
                print("✅ UTF-8 BOM detected (Power BI compatible)")
            else:
                print("⚠️  No UTF-8 BOM detected (may cause encoding issues)")
            
            file.seek(0)
            reader = csv.reader(file)
            
            # Read header
            header = next(reader)
            print(f"✅ Header row found with {len(header)} columns")
            print(f"   Columns: {', '.join(header)}")
            
            # Expected columns for Power BI
            expected_columns = [
                'Timestamp', 'SourceIP', 'DestinationIP', 'SourcePort', 
                'DestinationPort', 'Protocol', 'PacketLength', 'Direction', 
                'TCPFlags', 'ApplicationGuess'
            ]
            
            if header == expected_columns:
                print("✅ Header matches expected Power BI format")
            else:
                print("❌ Header does not match expected format")
                print(f"   Expected: {expected_columns}")
                print(f"   Found:    {header}")
                return False
            
            # Validate data rows
            row_count = 0
            valid_rows = 0
            issues = []
            
            for row_num, row in enumerate(reader, start=2):
                row_count += 1
                
                if len(row) != len(header):
                    issues.append(f"Row {row_num}: Column count mismatch ({len(row)} vs {len(header)})")
                    continue
                
                # Validate timestamp format
                try:
                    datetime.strptime(row[0].strip('"'), '%Y-%m-%d %H:%M:%S.%f')
                except ValueError:
                    try:
                        datetime.strptime(row[0].strip('"'), '%Y-%m-%d %H:%M:%S')
                    except ValueError:
                        issues.append(f"Row {row_num}: Invalid timestamp format: {row[0]}")
                        continue
                
                # Validate IP addresses
                source_ip = row[1].strip('"')
                dest_ip = row[2].strip('"')
                
                if not is_valid_ip(source_ip) and source_ip != "Unknown":
                    issues.append(f"Row {row_num}: Invalid source IP: {source_ip}")
                    continue
                
                if not is_valid_ip(dest_ip) and dest_ip != "Unknown":
                    issues.append(f"Row {row_num}: Invalid destination IP: {dest_ip}")
                    continue
                
                # Validate ports (should be integers or empty)
                try:
                    if row[3].strip() and not row[3].strip().isdigit():
                        issues.append(f"Row {row_num}: Invalid source port: {row[3]}")
                        continue
                    if row[4].strip() and not row[4].strip().isdigit():
                        issues.append(f"Row {row_num}: Invalid destination port: {row[4]}")
                        continue
                except:
                    pass
                
                # Validate packet length
                try:
                    int(row[6])
                except ValueError:
                    issues.append(f"Row {row_num}: Invalid packet length: {row[6]}")
                    continue
                
                valid_rows += 1
            
            print(f"✅ Data validation complete")
            print(f"   Total rows: {row_count}")
            print(f"   Valid rows: {valid_rows}")
            print(f"   Invalid rows: {row_count - valid_rows}")
            
            if issues:
                print(f"\n⚠️  Issues found:")
                for issue in issues[:10]:  # Show first 10 issues
                    print(f"   {issue}")
                if len(issues) > 10:
                    print(f"   ... and {len(issues) - 10} more issues")
            else:
                print("✅ No data validation issues found")
            
            # Power BI specific checks
            print(f"\n=== Power BI Compatibility Check ===")
            
            # Check for localhost traffic
            localhost_count = 0
            file.seek(0)
            reader = csv.reader(file)
            next(reader)  # Skip header
            
            for row in reader:
                if '127.0.0.1' in row[1] or '127.0.0.1' in row[2] or 'localhost' in row[1] or 'localhost' in row[2]:
                    localhost_count += 1
            
            print(f"✅ Localhost traffic detected: {localhost_count} packets")
            
            # Check protocol distribution
            protocols = {}
            file.seek(0)
            reader = csv.reader(file)
            next(reader)  # Skip header
            
            for row in reader:
                protocol = row[5].strip('"')
                protocols[protocol] = protocols.get(protocol, 0) + 1
            
            print(f"✅ Protocol distribution:")
            for protocol, count in sorted(protocols.items(), key=lambda x: x[1], reverse=True):
                print(f"   {protocol}: {count}")
            
            return len(issues) == 0
            
    except Exception as e:
        print(f"❌ ERROR reading file: {e}")
        return False

def is_valid_ip(ip):
    """Simple IP address validation"""
    if not ip or ip == "Unknown":
        return True
    
    parts = ip.split('.')
    if len(parts) != 4:
        return False
    
    try:
        for part in parts:
            num = int(part)
            if not 0 <= num <= 255:
                return False
        return True
    except ValueError:
        return False

def main():
    """Main validation function"""
    print("=== Power BI CSV Validation Tool ===")
    print("Validating generated CSV file for Power BI compatibility\n")
    
    csv_file = "output/captured_packets.csv"
    
    if validate_csv_file(csv_file):
        print("\n🎉 SUCCESS: CSV file is Power BI compatible!")
        print("You can now import this file into Power BI for visualization.")
    else:
        print("\n❌ FAILURE: CSV file has compatibility issues.")
        print("Please check the issues above and regenerate the file.")
    
    print(f"\nFile location: {os.path.abspath(csv_file)}")

if __name__ == "__main__":
    main()
