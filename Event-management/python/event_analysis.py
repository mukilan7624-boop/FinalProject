"""
College Event Management and Participation Analysis System
Course: 25ITC31 - Foundations of Data Science
Department of Information Technology, Velalar College of Engineering and Technology
Student Register No.: 31 | Academic Year: 2026 - 2027 (ODD)

Features:
1. Database Retrieval: Connects to SQLite database and queries normalized tables.
2. Data Preprocessing & Cleaning: Pandas missing value imputation, type casting, feature engineering.
3. Exploratory Data Analysis: NumPy statistics, grouping, pivot tables.
4. Data Visualization: Matplotlib & Seaborn plots exported to /visualizations.
"""

import os
import sqlite3
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns

# Set publication-quality plot style
sns.set_theme(style="whitegrid", palette="muted")
plt.rcParams['font.sans-serif'] = 'Segoe UI'
plt.rcParams['font.family'] = 'sans-serif'
plt.rcParams['figure.dpi'] = 300

DB_PATH = os.path.join(os.path.dirname(__file__), "..", "database", "event_management.db")
SCHEMA_PATH = os.path.join(os.path.dirname(__file__), "..", "database", "schema.sql")
SEED_PATH = os.path.join(os.path.dirname(__file__), "..", "database", "seed_data.sql")
OUTPUT_DIR = os.path.join(os.path.dirname(__file__), "visualizations")

def ensure_database_exists():
    """Initializes the SQLite database with schema and seed data if not already created."""
    os.makedirs(os.path.dirname(DB_PATH), exist_ok=True)
    conn = sqlite3.connect(DB_PATH)
    cursor = conn.cursor()
    cursor.execute("SELECT count(name) FROM sqlite_master WHERE type='table' AND name='events'")
    if cursor.fetchone()[0] == 0:
        print("[*] Initializing SQLite database from schema.sql and seed_data.sql...")
        if os.path.exists(SCHEMA_PATH) and os.path.exists(SEED_PATH):
            with open(SCHEMA_PATH, 'r', encoding='utf-8') as f:
                cursor.executescript(f.read())
            with open(SEED_PATH, 'r', encoding='utf-8') as f:
                cursor.executescript(f.read())
            conn.commit()
            print("[+] Database initialized successfully.")
    conn.close()

def retrieve_datasets():
    """Retrieves all normalized tables from the relational database into Pandas DataFrames."""
    conn = sqlite3.connect(DB_PATH)
    
    print("[1/5] Extracting SQL tables into Pandas DataFrames...")
    df_events = pd.read_sql_query("SELECT * FROM events", conn)
    df_students = pd.read_sql_query("SELECT * FROM students", conn)
    df_registrations = pd.read_sql_query("SELECT * FROM registrations", conn)
    df_attendance = pd.read_sql_query("SELECT * FROM attendance", conn)
    df_feedback = pd.read_sql_query("SELECT * FROM feedback", conn)
    
    conn.close()
    return df_events, df_students, df_registrations, df_attendance, df_feedback

def preprocess_and_merge(df_events, df_students, df_registrations, df_attendance, df_feedback):
    """
    Cleans, casts data types, handles null values, and merges tables into a master analytics DataFrame.
    Fulfills 25ITC31 Unit 3 (Data Wrangling) and Unit 4 (Data Cleaning & Transformation).
    """
    print("[2/5] Preprocessing and merging relational datasets...")

    # Data type conversion (Unit 4: Type Conversion)
    df_events['event_date'] = pd.to_datetime(df_events['event_date'])
    df_registrations['registration_date'] = pd.to_datetime(df_registrations['registration_date'])
    df_attendance['marked_at'] = pd.to_datetime(df_attendance['marked_at'])
    df_feedback['submitted_at'] = pd.to_datetime(df_feedback['submitted_at'])

    # Merging tables (Unit 4: Data Integration using Merging)
    master_df = df_registrations.merge(df_events, on='event_id', how='left', suffixes=('_reg', '_event'))
    master_df = master_df.merge(df_students, on='student_id', how='left', suffixes=('', '_student'))
    master_df = master_df.merge(df_attendance, on='registration_id', how='left', suffixes=('', '_att'))
    master_df = master_df.merge(df_feedback, on='registration_id', how='left', suffixes=('', '_fb'))

    # Handling Missing Values (Unit 4: Handling Missing Values)
    master_df['status_att'] = master_df['status_att'].fillna('NOT_MARKED')
    master_df['comments'] = master_df['comments'].fillna('No comments provided')
    
    # Feature Creation (Unit 4: Feature Creation)
    master_df['attended_binary'] = master_df['status_att'].apply(lambda x: 1 if x == 'PRESENT' else 0)

    return master_df

def print_exploratory_data_analysis(master_df, df_events):
    """Prints descriptive statistics, aggregations, and summary tables (Unit 1 & Unit 5)."""
    print("\n" + "="*80)
    print("                EXPLORATORY DATA ANALYSIS & SUMMARY STATISTICS                ")
    print("="*80)
    print(f"Total Events Tracked:        {len(df_events)}")
    print(f"Total Student Registrations: {len(master_df)}")
    print(f"Total Physical Attendees:    {master_df['attended_binary'].sum()}")
    overall_turnout = (master_df['attended_binary'].sum() / len(master_df)) * 100
    print(f"Overall Turnout Rate:        {overall_turnout:.2f}%")
    
    valid_ratings = master_df['rating'].dropna()
    print(f"Average Feedback Rating:     {valid_ratings.mean():.2f} / 5.0 (Std Dev: {valid_ratings.std():.2f})")
    print(f"Feedback Rating Median:      {valid_ratings.median():.1f} | 25th %ile: {valid_ratings.quantile(0.25)} | 75th %ile: {valid_ratings.quantile(0.75)}")
    print("="*80 + "\n")

def generate_visualizations(master_df, df_events):
    """
    Generates 6 distinct visualization charts using Matplotlib and Seaborn.
    Fulfills 25ITC31 Unit 5: Bar Charts, Pie Charts, Histograms, Box Plots, and Heatmaps.
    """
    os.makedirs(OUTPUT_DIR, exist_ok=True)
    print("[3/5] Generating publication-quality charts...")

    # --------------------------------------------------------------------------
    # Chart 1: Event Category Distribution (Pie Chart & Bar Chart)
    # --------------------------------------------------------------------------
    fig, axes = plt.subplots(1, 2, figsize=(14, 5))
    category_counts = df_events['category'].value_counts()
    colors = sns.color_palette("Set2", len(category_counts))

    axes[0].pie(category_counts, labels=category_counts.index, autopct='%1.1f%%',
                startangle=140, colors=colors, explode=[0.05]*len(category_counts),
                wedgeprops=dict(edgecolor='white', linewidth=1.5))
    axes[0].set_title("Event Distribution by Category (Pie Chart)", fontsize=13, fontweight='bold')

    sns.barplot(x=category_counts.index, y=category_counts.values, ax=axes[1], palette="Blues_d")
    axes[1].set_title("Number of Events per Category", fontsize=13, fontweight='bold')
    axes[1].set_ylabel("Count of Events")
    axes[1].set_xlabel("Category")
    for i, v in enumerate(category_counts.values):
        axes[1].text(i, v + 0.05, str(v), ha='center', fontweight='bold')

    plt.tight_layout()
    chart1_path = os.path.join(OUTPUT_DIR, "1_event_category_distribution.png")
    plt.savefig(chart1_path, dpi=300)
    plt.close()
    print(f"  [+] Saved: {chart1_path}")

    # --------------------------------------------------------------------------
    # Chart 2: Registration Count vs. Maximum Capacity (Grouped Bar Chart)
    # --------------------------------------------------------------------------
    event_reg_summary = master_df.groupby(['event_id', 'event_name', 'max_capacity']).size().reset_index(name='registrations')
    
    plt.figure(figsize=(12, 6))
    bar_width = 0.35
    indices = np.arange(len(event_reg_summary))

    plt.bar(indices - bar_width/2, event_reg_summary['max_capacity'], width=bar_width,
            label='Max Capacity', color='#93c5fd', edgecolor='#2563eb')
    plt.bar(indices + bar_width/2, event_reg_summary['registrations'], width=bar_width,
            label='Confirmed Registrations', color='#1d4ed8', edgecolor='#1e3a8a')

    # Truncate long event titles
    short_labels = [name[:20] + '...' if len(name) > 20 else name for name in event_reg_summary['event_name']]
    plt.xticks(indices, short_labels, rotation=25, ha='right')
    plt.title("Event Capacity vs. Confirmed Registrations (Reg No: 31)", fontsize=14, fontweight='bold')
    plt.xlabel("Event Name")
    plt.ylabel("Number of Seats")
    plt.legend()
    plt.tight_layout()
    chart2_path = os.path.join(OUTPUT_DIR, "2_registration_vs_capacity.png")
    plt.savefig(chart2_path, dpi=300)
    plt.close()
    print(f"  [+] Saved: {chart2_path}")

    # --------------------------------------------------------------------------
    # Chart 3: Department-Wise Student Participation (Horizontal Bar Plot)
    # --------------------------------------------------------------------------
    dept_counts = master_df['department'].value_counts()
    
    plt.figure(figsize=(10, 5))
    sns.barplot(y=dept_counts.index, x=dept_counts.values, palette="crest")
    plt.title("Department-Wise Student Participation Volume", fontsize=14, fontweight='bold')
    plt.xlabel("Total Registrations")
    plt.ylabel("Department")
    for i, v in enumerate(dept_counts.values):
        plt.text(v + 0.2, i, str(v), va='center', fontweight='bold')
    plt.tight_layout()
    chart3_path = os.path.join(OUTPUT_DIR, "3_department_wise_participation.png")
    plt.savefig(chart3_path, dpi=300)
    plt.close()
    print(f"  [+] Saved: {chart3_path}")

    # --------------------------------------------------------------------------
    # Chart 4: Attendance Conversion Rate by Event (Bar Chart)
    # --------------------------------------------------------------------------
    event_turnout = master_df.groupby('event_name').agg(
        registered=('registration_id', 'count'),
        attended=('attended_binary', 'sum')
    ).reset_index()
    event_turnout['turnout_rate'] = (event_turnout['attended'] / event_turnout['registered']) * 100

    plt.figure(figsize=(11, 5))
    bars = sns.barplot(x='turnout_rate', y='event_name', data=event_turnout.sort_values(by='turnout_rate', ascending=False), palette="viridis")
    plt.title("Event Attendance Conversion Rate (% Present / Registered)", fontsize=14, fontweight='bold')
    plt.xlabel("Attendance Turnout Rate (%)")
    plt.ylabel("Event Name")
    plt.xlim(0, 115)
    for p in bars.patches:
        width = p.get_width()
        bars.annotate(f"{width:.1f}%", (width + 1.5, p.get_y() + p.get_height() / 2.),
                      ha='left', va='center', fontweight='bold', fontsize=10)
    plt.tight_layout()
    chart4_path = os.path.join(OUTPUT_DIR, "4_attendance_rate_by_event.png")
    plt.savefig(chart4_path, dpi=300)
    plt.close()
    print(f"  [+] Saved: {chart4_path}")

    # --------------------------------------------------------------------------
    # Chart 5: Feedback Rating Distribution (Seaborn Countplot / Boxplot)
    # --------------------------------------------------------------------------
    fig, axes = plt.subplots(1, 2, figsize=(13, 5))
    valid_feedback = master_df.dropna(subset=['rating'])

    sns.countplot(x='rating', data=valid_feedback, ax=axes[0], palette="YlOrRd")
    axes[0].set_title("Frequency of Feedback Ratings (1 to 5 Stars)", fontsize=13, fontweight='bold')
    axes[0].set_xlabel("Star Rating")
    axes[0].set_ylabel("Count")

    sns.boxplot(x='category', y='rating', data=valid_feedback, ax=axes[1], palette="Pastel1")
    axes[1].set_title("Rating Distribution by Event Category (Box Plot)", fontsize=13, fontweight='bold')
    axes[1].set_xlabel("Event Category")
    axes[1].set_ylabel("Rating (1-5)")

    plt.tight_layout()
    chart5_path = os.path.join(OUTPUT_DIR, "5_feedback_ratings_distribution.png")
    plt.savefig(chart5_path, dpi=300)
    plt.close()
    print(f"  [+] Saved: {chart5_path}")

    # --------------------------------------------------------------------------
    # Chart 6: Correlation Matrix Heatmap (Multivariate Analysis)
    # --------------------------------------------------------------------------
    # Aggregate by event to calculate numerical correlation metrics
    agg_corr = master_df.groupby('event_id').agg(
        max_capacity=('max_capacity', 'first'),
        total_registered=('registration_id', 'count'),
        total_attended=('attended_binary', 'sum'),
        avg_rating=('rating', 'mean')
    ).reset_index()
    agg_corr['attendance_pct'] = (agg_corr['total_attended'] / agg_corr['total_registered']) * 100
    corr_matrix = agg_corr[['max_capacity', 'total_registered', 'total_attended', 'attendance_pct', 'avg_rating']].corr()

    plt.figure(figsize=(8, 6))
    sns.heatmap(corr_matrix, annot=True, cmap="coolwarm", fmt=".2f", linewidths=1, linecolor='white')
    plt.title("Multivariate Correlation Heatmap (Engagement Metrics)", fontsize=13, fontweight='bold')
    plt.tight_layout()
    chart6_path = os.path.join(OUTPUT_DIR, "6_correlation_engagement_heatmap.png")
    plt.savefig(chart6_path, dpi=300)
    plt.close()
    print(f"  [+] Saved: {chart6_path}")

    print("\n[+] All 6 visualizations successfully saved to: " + OUTPUT_DIR)

def main():
    print("="*80)
    print(" VELALAR COLLEGE OF ENGINEERING AND TECHNOLOGY | IT DEPARTMENT")
    print(" DATA SCIENCE ANALYSIS PIPELINE: COLLEGE EVENT MANAGEMENT (REG: 31)")
    print("="*80)
    ensure_database_exists()
    df_events, df_students, df_registrations, df_attendance, df_feedback = retrieve_datasets()
    master_df = preprocess_and_merge(df_events, df_students, df_registrations, df_attendance, df_feedback)
    print_exploratory_data_analysis(master_df, df_events)
    generate_visualizations(master_df, df_events)
    print("\n[+] Data Science Analytics and Visualization Pipeline Execution Complete!")

if __name__ == "__main__":
    main()
